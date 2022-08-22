package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.response.ImagesResponseDto;
import com.hanghae.clone_project.dto.response.productDatail.ApiResponseMessage;
import com.hanghae.clone_project.dto.response.productDatail.ResponseDto;
import com.hanghae.clone_project.dto.response.productDatail.ProductDetailImageResponseDto;
import com.hanghae.clone_project.dto.response.productDatail.ProductDetailResponseDto;
import com.hanghae.clone_project.entity.Image;
import com.hanghae.clone_project.entity.ProductDetail;
import com.hanghae.clone_project.entity.Product;
import com.hanghae.clone_project.repository.ImageRepository;
import com.hanghae.clone_project.repository.ProductDetailRepository;
import com.hanghae.clone_project.repository.ProductRepository;
import com.hanghae.clone_project.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductRepository productRepository;

    private final ImageRepository imageRepository;
    private final ProductDetailRepository productDetailRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public ResponseEntity<ApiResponseMessage> createDetail(Long productId,
                                                           List<MultipartFile> multipartFile) throws IOException {

        Product product = productRepository.findById(productId).orElseThrow(() ->
            new IllegalArgumentException("게시물이 존제하지 않습니다."));

        //복수의 Url이 하나하나씩 자동으로 리스트에 들어감
        //product와 같이 출력하는 용도(지금은 필요없음)
        List<String> imageUrlDetails = awsS3Service.uploadFile(multipartFile);
        List<ProductDetail> productDetailList = new ArrayList<>();
        if (imageUrlDetails == null) {
            ApiResponseMessage message = new ApiResponseMessage("Fail", "Upload Fail", "", "");
            return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
        for (String imageUrlDetail : imageUrlDetails) {
            //(오류)imageUrlDetail에서 에러 발생 -> (해결)enttity 생성자에 void를 빼니 작동
            ProductDetail productDetail = new ProductDetail(imageUrlDetail);
            productDetailList.add(productDetail);
        }


        //이미지 하나하나 저장하기
        //productDetail에 저장하는 용도
        for (String imageUrlDetail :imageUrlDetails) {
            productDetailRepository.save(ProductDetail.builder()
                    .product(product)
                    .detailImage_Url(imageUrlDetail)
                    .build());
        }

        ApiResponseMessage message = new ApiResponseMessage("Success", "게시글 등록", "", "");
        //new를 넣어줘야함.
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }


    //게시글 출력하기
    public ResponseDto<ProductDetailResponseDto> getProductDetail(Long productId) {
        //1. 해당 게시글 찾아오기
        Product product = productRepository.findById(productId).orElseThrow(()->
                new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        //2.게시글로 ProductDetail 가져오기
        //repository에 product기준으로 찾는 객체를 선언해주어야 함.
        //productId를 기준으로 찾는 것이 아니라 product를 기준으로 찾음.
        List<ProductDetail> productDetails = productDetailRepository.findAllByProduct(product);
        if (productDetails == null) {
            return ResponseDto.fail("PRODUCTDETAIL_NOT_FOUNDED","상세페이지를 찾을 수 없습니다.");
        }

// DetailImage 리스트 안에 배열을 출력시키는 로직
//        List<ProductDetailImageResponseDto> detailList = new ArrayList<>();
//        for (ProductDetail productDetail : productDetails) {
//            //(오류)new ProductDetailResponseDto(productDetail) -> 타입을 일치시켜줘야 함.
//            ProductDetailImageResponseDto productDetailImageResponseDto = new ProductDetailImageResponseDto(productDetail);
//            detailList.add(productDetailImageResponseDto);
//        }

// 기본 이미지 : 리스트 안에 배열을 출력시키는 로직
        //(오류)List<ImagesResponseDto> imageLists = imageRepository.findAllByProduct(product);
//        List<Image> images = imageRepository.findAllByProduct(product);
//        List<ImagesResponseDto> imageLists = new ArrayList<>();
//        for (Image image : images){
//            ImagesResponseDto imageResponseDto = new ImagesResponseDto(image);
//            imageLists.add(imageResponseDto);
//        }
//        if (images == null) {
//            ResponseDto.fail("IMAGE+NOT_FOUNDED","이미지를 찾을 수 없습니다.");
//        }

        List<Image> imageIm = imageRepository.findAllByProduct(product);
        List<String> imageStrList = new ArrayList<>();

        for (Image images : imageIm) {
            String imageStr = "";
            imageStr = images.getImageUrl();
            imageStrList.add(imageStr);
        }

        List<ProductDetail> imageDeIm = productDetailRepository.findAllByProduct(product);
        List<String> imageDeStrList = new ArrayList<>();

        for (ProductDetail images : imageDeIm) {
            String imageStr = "";
            imageStr = images.getDetailImage_Url();
            imageDeStrList.add(imageStr);
        }




        //이중 연관관계절차도 DetailDto <-(리스트로 변환)- imageDto <-(연결)- imageEntity
        //for(ImagesResponseDto image : imageLists)
            //imageLists type String
        return ResponseDto.success(ProductDetailResponseDto.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .content(product.getContent())
                .category(product.getCategory())
                .image_Url(imageStrList)
                .detailImage_Url(imageDeStrList)
                .build());

    }

    //상세페이지 삭제
//    public ResponseEntity<?> deleteDetail(Long productId){
//        ProductDetail productDetail = productDetailRepository.findAllByProductId(productId)
//                .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));
//        productDetailRepository.delete(productDetail);
//        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
//    }

    //상세페이지 삭제
    @Transactional
   public ResponseEntity<?> deleteDetail(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        List<ProductDetail> detailLists = productDetailRepository.findAllByProductId(productId);
        for (ProductDetail detail : detailLists){
            String imageUrl = detail.getDetailImage_Url();
            awsS3Service.deleteFile(imageUrl);
            productDetailRepository.delete(detail);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    //상세페이지 수정
    @Transactional
    public ResponseEntity<?> updateDetail(Long productId, List<MultipartFile> multipartFile){
        ProductDetail productDetail = productDetailRepository.findByProductId(productId)
                .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        List<ProductDetail> detailLists = productDetailRepository.findAllByProductId(productId);
        for (ProductDetail detail : detailLists){
            String imageUrl = detail.getDetailImage_Url();
            awsS3Service.deleteFile(imageUrl);
            productDetailRepository.delete(detail);
        }

        //이미지 파일은 Url은 String 이니 타입을 재지정해주어야 함.
        List<String> productDetailImage = awsS3Service.uploadFile(multipartFile);
        //List<ProductDetail> detailImageList = new ArrayList<>();
        for (String detailImage : productDetailImage){
            ProductDetail productDetail1 = new ProductDetail(detailImage);
            detailLists.add(productDetail1);
        }

        for (ProductDetail productDetail1 : detailLists){
            productDetailRepository.save(productDetail1);
        }

        return new ResponseEntity<>("파일이 수정되었습니다.", HttpStatus.OK);
    }
}


