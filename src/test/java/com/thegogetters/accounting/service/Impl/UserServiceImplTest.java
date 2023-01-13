package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.RoleDTO;
import com.thegogetters.accounting.dto.TestDocumentInitializer;
import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.User;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.UserRepository;
import com.thegogetters.accounting.service.SecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @InjectMocks
    UserServiceImpl userService; // this is our real service imp where we inject or the mocks

    @Mock
    UserRepository userRepository;

    @Mock
    SecurityService securityService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Spy
    MapperUtil mapperUtil = new MapperUtil(new ModelMapper()); //since mapperUtil is just converting it is not affecting business logic so we won't test it



    @Test
    @DisplayName("When find by Id then success")
    public void GIVEN_ID_WHEN_FIND_BY_ID_THEN_SUCCESS() throws AccountingAppException {
        //Given
        UserDTO userDTO = TestDocumentInitializer.getUser("Admin");
        User user = mapperUtil.convert(userDTO, new User());
        //when
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.ofNullable(user));
        //Then
        var returnedUser = userService.findById(userDTO.getId());
        assertThat(returnedUser.getFirstname().equals(userDTO.getFirstname()));

    }


    @Test
    @DisplayName("When find by username then success")
    public void  GIVEN_USERNAME_WHEN_FIND_BY_USERNAME_THEN_SUCCESS(){
        //Given
        UserDTO userDTO = TestDocumentInitializer.getUser("Admin");
        User user = mapperUtil.convert(userDTO, new User());
        //when
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(user);
        //Then
        var returnedUser = userService.findByUserName(userDTO.getUsername());
        assertThat(returnedUser.getCompany().getTitle().equals(userDTO.getCompany().getTitle()));
    }


    @Test
    @DisplayName("Given id when delete then success")
    public void GIVEN_ID_WHEN_DELETE_THEN_SUCCESS() throws AccountingAppException {
        // Given
        UserDTO userDto = TestDocumentInitializer.getUser("Admin");
        User user = mapperUtil.convert(userDto, new User());
        // When
        doReturn(Optional.ofNullable(user)).when(userRepository).findById(anyLong());

        userService.deleteById(anyLong());
        // Then
        verify(userRepository).save(any(User.class));
    }


    @Test
    @DisplayName("Given UserDto when save then success")
    public void GIVEN_USER_DTO_WHEN_SAVE_THEN_SUCCESS(){
        // Given
        String testPassword = "Cydeo2023!";
        UserDTO userDto = TestDocumentInitializer.getUser("Admin");
        doReturn(testPassword).when(passwordEncoder).encode(anyString());
        // When
        userService.save(userDto);
        // Then
        verify(userRepository).save(any(User.class));

    }



    @Test
    @DisplayName("Given UserName when exist then success")
    public void GIVEN_USERNAME_WHEN_EXIST_THEN_SUCCESS(){
        //Given
        String username = "cydeo@gmail.com";
        UserDTO userDTO = TestDocumentInitializer.getUser("Admin");
        User user = mapperUtil.convert(userDTO, new User());
        //when
        userService.usernameExist(username);
        // Then
        verify(userRepository).existsByUsername(username);

    }




}