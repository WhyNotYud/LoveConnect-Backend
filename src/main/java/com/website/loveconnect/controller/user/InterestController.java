package com.website.loveconnect.controller.user;


import com.website.loveconnect.dto.request.InterestDTO;
import com.website.loveconnect.dto.response.ApiResponse;
import com.website.loveconnect.entity.Interest;
import com.website.loveconnect.service.InterestService;
import com.website.loveconnect.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InterestController {

    UserService userService;
    InterestService interestService;

    // Request Get all Interest
    @GetMapping("/user/interest/{idUser}")
    public ResponseEntity<ApiResponse<?>> getAllInterest(@PathVariable Integer idUser) {
        try {
            log.info("Request lấy danh sách sở thích của user có ID : {}" , idUser);
            List<Interest> Interests = interestService.getAllInterest(idUser);
            return ResponseEntity.ok(new ApiResponse<>(true,"Get interests successful", Interests));
        }catch (Exception ex) {
            log.error("--->>> Không thể lấy được danh sách sở thích vì : {}" , ex.getMessage() , ex.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Request Add one Interest
    @PostMapping("/user/interest/add/{idUser}")
    public ResponseEntity<ApiResponse<?>> addInterest(@RequestBody InterestDTO interestDTO , @PathVariable Integer idUser) {
        try {
            log.info("Request THÊM sở thích vào danh sách của user có ID : {}" , idUser);
            interestService.addInterest(idUser , interestDTO);
            return ResponseEntity.ok(new ApiResponse<>(true,"Added interest successful", null));
        }catch (Exception ex) {
            log.error("--->>> Không thể thêm sở thích vì : {}" , ex.getMessage() , ex.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Request Update one Interest
    @PutMapping("/user/interest/update/{idUser}/{idInterest}")
    public ResponseEntity<ApiResponse<?>> updateInterest(@RequestBody InterestDTO interestDTO
            , @PathVariable Integer idUser
            , @PathVariable Integer idInterest) {
        try {
            log.info("Request SỬA sở thích vào danh sách của user có ID : {}" , idUser);
            interestService.updateInterest(idInterest, idUser, interestDTO);
            return ResponseEntity.ok(new ApiResponse<>(true,"Updated interest successful", null));
        }catch (Exception ex) {
            log.error("--->>> Không thể sửa được sở thích vì : {}" , ex.getMessage() , ex.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Request Delete one Interest
    @DeleteMapping("/user/interest/delete/{idUser}/{idInterest}")
    public ResponseEntity<ApiResponse<?>> deleteInterest(@PathVariable Integer idUser , @PathVariable Integer idInterest) {
        try {
            log.info("Request SỬA sở thích vào danh sách của user có ID : {}" , idUser);
            interestService.deleterInterest(idUser , idInterest);
            return ResponseEntity.ok(new ApiResponse<>(true,"Deleted interest successful", null));
        }catch (Exception ex) {
            log.error("--->>> Không thể xóa được sở thích vì : {}" , ex.getMessage() , ex.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //lấy danh sách đầy đủ sở thích
    @GetMapping(value = "/interest-name")
    public ResponseEntity<ApiResponse<List<String>>> getInterestName() {
        return ResponseEntity.ok(new ApiResponse<>(true,"Get list interest name successful",
                interestService.findAllInterestName()));
    }
}
