package com.website.loveconnect.controller.admin;

import com.website.loveconnect.dto.request.UserUpdateRequest;
import com.website.loveconnect.dto.response.ApiResponse;
import com.website.loveconnect.dto.response.ListUserResponse;
import com.website.loveconnect.dto.response.UserUpdateResponse;
import com.website.loveconnect.dto.response.UserViewResponse;
import com.website.loveconnect.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "http://127.0.0.1:5500")  chi ap dung local
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminUserController {

    UserService userService;

    //lấy tất cả người dùng
    @GetMapping(value = "/users")
    public ResponseEntity<ApiResponse<Page<ListUserResponse>>> getAllUser(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        return  ResponseEntity.ok(new ApiResponse<>(true,"get list user successful",userService.getAllUser(page,size)));
    }

    //lấy thông tin chi tiết người dùng
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<ApiResponse<UserViewResponse>> getUserById(@PathVariable int userId) {
        UserViewResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse<>(true,"Get user successful",user));
    }

    //block người dùng bằng id
    @PutMapping(value = "/users/{userId}/block")
    public ResponseEntity<ApiResponse<String>> blockUser(@PathVariable int userId) {
        userService.blockUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true,"User blocked successfully", null));
    }

    //unblock người dùng bằng id
    @PutMapping(value = "/users/{userId}/unblock")
    public ResponseEntity<ApiResponse<String>> unblockUser(@PathVariable int userId) {
        userService.unblockUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true,"User unblocked successfully", null));
    }

    @GetMapping(value = "/users/{userId}/update")
    public ResponseEntity<ApiResponse<UserUpdateResponse>> getUserUpdateById(@PathVariable int userId) {
        UserUpdateResponse user = userService.getUserUpdateById(userId);
        return ResponseEntity.ok(new ApiResponse<>(true,"Get user successful",user));
    }


    @PutMapping(value = "/users/{userId}/update")
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateUserById(@PathVariable int userId,
                                                                          @RequestBody UserUpdateRequest userUpdateRequest) {
        UserUpdateResponse user = userService.updateUser(userId,userUpdateRequest);
        return ResponseEntity.ok(new ApiResponse<>(true,"Get user successful",user));
    }

    @DeleteMapping(value = "/users/{userId}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
