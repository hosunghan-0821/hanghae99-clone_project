package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.request.ProductRequestDto;
import com.hanghae.clone_project.dto.response.ProductResponseDto;
import com.hanghae.clone_project.dto.response.ProductsResponseDto;
import com.hanghae.clone_project.dto.responseDto.ResponseDto;
import com.hanghae.clone_project.entity.Image;
import com.hanghae.clone_project.entity.Product;
import com.hanghae.clone_project.repository.ImageRepository;
import com.hanghae.clone_project.repository.ProductRepository;
import com.hanghae.clone_project.s3.AwsS3Service;
import com.hanghae.clone_project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final AwsS3Service awsS3Service;

    private final ImageRepository imageRepository;

    //List<MultipartFile> multipartFile -> main과 detail파일로 나누기
    @Transactional
    public ResponseEntity<?> createProduct(ProductRequestDto requestDto,
                                           List<MultipartFile> mainMultipartFile,
                                           List<MultipartFile> detailMultipartFile,
                                           UserDetailsImpl userDetails) {

//        main image를 url형태로 DB에 저장
        List<String> mainImageUrls = awsS3Service.uploadFile(mainMultipartFile);
        List<Image> mainImageList = new ArrayList<>();
        for (String mainImageUrl : mainImageUrls) {
            Image image = new Image(mainImageUrl);
            mainImageList.add(image);
        }

//        detail image를 url형태로 DB에 저장
            List<String> detailImageurls = awsS3Service.uploadFile(detailMultipartFile);
            List<Image> detailImageList = new ArrayList<>();
            for (String detailImageurl : detailImageurls) {
                Image image = new Image(detailImageurl);
                detailImageList.add(image);
            }

            Product product = Product.builder()
                    .title(requestDto.getTitle())
                    .price(requestDto.getPrice())
                    .content(requestDto.getContent())
                    .category(requestDto.getCategory())
                    .imageUrlList(mainImageList)
                    .imageUrlList(detailImageList)
                    .user(userDetails.getUser())
                    .build();

            productRepository.save(product);

        for (String mainImageUrl : mainImageUrls) {
            imageRepository.save(Image.builder()
                    .product(product)
                    .category("main")
                    .imageUrl(mainImageUrl)
                    .build());
        }

        for (String detailImageUrl : detailImageurls) {
            imageRepository.save(Image.builder()
                    .product(product)
                    .category("detail")
                    .imageUrl(detailImageUrl)
                    .build());
        }

            return new ResponseEntity<>("상품이 등록되었습니다.", HttpStatus.CREATED);
        }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readAllProduct(String category) {
        List<Product> products;
        if(category==null){
            products = productRepository.findByOrderByCreatedAtDesc();
        }
        else{
            products=productRepository.findByCategoryOrderByCreatedAtDesc(category);
        }

        List<ProductsResponseDto> productList = new ArrayList<>();

        for (Product product : products){
            List<Image> mainImages = imageRepository.findAllByCategoryAndProduct("main", product);
            List<String> mainImageUrlList = new ArrayList<>();

            List<Image> detailImages = imageRepository.findAllByCategoryAndProduct("detail", product);
            List<String> detailImageUrlList = new ArrayList<>();

            for (Image detailImage : detailImages){
                String imageUrl = detailImage.getImageUrl();
                detailImageUrlList.add(imageUrl);
            }

            for (Image mainImage : mainImages){
                String mainImageUrl = mainImage.getImageUrl();
                mainImageUrlList.add(mainImageUrl);
            }
            productList.add(ProductsResponseDto.builder()
                            .productId(product.getId())
                            .title(product.getTitle())
                            .price(product.getPrice())
                            .category(product.getCategory())
                            .mainImageUrl(mainImageUrlList)
                            .detailImageUrl(detailImageUrlList)
                            .build());
        }

        return productList;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getMainItems(){

        List<Product> products = productRepository.findTop6ByOrderByIdAsc();
        List<ProductsResponseDto> responseDtos = new ArrayList<>();
        for(Product product: products){
            responseDtos.add(ProductsResponseDto.of(product));
        }
        return new ResponseEntity<>(ResponseDto.success(responseDtos),HttpStatus.OK);
    }


    //상품 상세조회
    public ProductResponseDto readProduct(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );

        List<Image> mainImages = imageRepository.findAllByCategoryAndProduct("main", product);
        List<String> mainImageUrlList = new ArrayList<>();

        for (Image mainImage : mainImages){
            String mainImageUrl = mainImage.getImageUrl();
            mainImageUrlList.add(mainImageUrl);
        }

        List<Image> detailImages = imageRepository.findAllByCategoryAndProduct("detail", product);
        List<String> detailImageUrlList = new ArrayList<>();

        for (Image detailImage : detailImages){
            String imageUrl = detailImage.getImageUrl();
            detailImageUrlList.add(imageUrl);
        }

        return ProductResponseDto.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .content(product.getContent())
                .category(product.getCategory())
                .mainImageUrl(mainImageUrlList)
                .detailImageUrl(detailImageUrlList)
                .build();
    }

    @Transactional
    public ResponseEntity<?> updateProduct(Long productId,
                                           ProductRequestDto requestDto,
                                           UserDetailsImpl userDetails) {

        if (!productRepository.findById(productId).isPresent()){
            return new ResponseEntity<>("해당 상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        Product product = productRepository.findById(productId).get();

        if (!product.getUser().getUsername().equals(userDetails.getUser().getUsername())){
            return new ResponseEntity<>("상품등록자만 수정할 수 있습니다.", HttpStatus.UNAUTHORIZED);
        }
        product.update(requestDto);

        return new ResponseEntity<>("상품이 수정되었습니다.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteProduct(Long productId) {

        if (!productRepository.findById(productId).isPresent()){
            return new ResponseEntity<>("해당 상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        Product product = productRepository.findById(productId).get();
        productRepository.delete(product);

        return new ResponseEntity<>("상품이 삭제되었습니다.", HttpStatus.OK);
    }

}
