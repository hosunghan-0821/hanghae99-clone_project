package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.request.ProductRequestDto;
import com.hanghae.clone_project.dto.response.ImagesResponseDto;
import com.hanghae.clone_project.dto.response.ProductResponseDto;
import com.hanghae.clone_project.dto.response.ProductsResponseDto;
import com.hanghae.clone_project.entity.Image;
import com.hanghae.clone_project.entity.Product;
import com.hanghae.clone_project.exception.CustomException;
import com.hanghae.clone_project.repository.ImageRepository;
import com.hanghae.clone_project.repository.ProductRepository;
import com.hanghae.clone_project.s3.AwsS3Service;
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

    @Transactional
    public ResponseEntity<?> createProduct(ProductRequestDto requestDto,
                                           List<MultipartFile> multipartFile) {

//         url형태로 DB에 저장
        List<String> imageUrls = awsS3Service.uploadFile(multipartFile);
        List<Image> imageUrlList = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            Image image = new Image(imageUrl);
            imageUrlList.add(image);
        }

            Product product = Product.builder()
                    .title(requestDto.getTitle())
                    .price(requestDto.getPrice())
                    .content(requestDto.getContent())
                    .category(requestDto.getCategory())
                    .imageUrlList(imageUrlList)
                    .build();

            productRepository.save(product);

            for (String imageUrl : imageUrls){
                imageRepository.save(Image.builder()
                        .product(product)
                        .category(product.getCategory())
                        .imageUrl(imageUrl)
                        .build());
            }

            return new ResponseEntity<>("상품이 등록되었습니다.", HttpStatus.CREATED);
        }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> getAllProduct() {

        List<Product> products = productRepository.findByOrderByCreatedAtDesc();
        List<ProductsResponseDto> productList = new ArrayList<>();

        for (Product product : products){
            List<Image> imageUrls = imageRepository.findAllByProduct(product);
            List<ImagesResponseDto> imageUrlList = new ArrayList<>();

            for (Image imageUrl : imageUrls){
                ImagesResponseDto imagesResponseDto = new ImagesResponseDto(imageUrl);
                imageUrlList.add(imagesResponseDto);
            }
            productList.add(ProductsResponseDto.builder()
                            .productId(product.getId())
                            .title(product.getTitle())
                            .price(product.getPrice())
                            .category(product.getCategory())
                            .imageUrl(imageUrlList)
                            .build());
        }

        return productList;
    }

    //상품 상세조회
    public ProductResponseDto getProduct(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );

        List<Image> imageUrls = imageRepository.findAllByProduct(product);
        List<ImagesResponseDto> imageUrlList = new ArrayList<>();

        for (Image imageUrl : imageUrls){
            ImagesResponseDto imagesResponseDto = new ImagesResponseDto(imageUrl);
            imageUrlList.add(imagesResponseDto);
        }

        return ProductResponseDto.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .content(product.getContent())
                .category(product.getCategory())
                .imageUrl(imageUrlList)
                .build();
    }

    @Transactional
    public ResponseEntity<?> updateProduct(Long productId, ProductRequestDto requestDto) {

        if (!productRepository.findById(productId).isPresent()){
            return new ResponseEntity<>("해당 상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        Product product = productRepository.findById(productId).get();
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
