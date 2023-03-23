package com.example.BookMyShow.Convertors;

import com.example.BookMyShow.Entities.UserEntity;
import com.example.BookMyShow.EntryDtos.UserEntryDto;

public class UserConvertor {

    //static is kept to avoid calling it via objects/instances
    public static UserEntity convertDtoToEntity(UserEntryDto userEntryDto){

        return UserEntity.builder().age(userEntryDto.getAge()).address(userEntryDto.getAddress())
                .email(userEntryDto.getEmail()).mobNo(userEntryDto.getMobNo()).name(userEntryDto.getName()).build();
    }
}
