package com.example.BookMyShow.Convertors;

import com.example.BookMyShow.Entities.ShowEntity;
import com.example.BookMyShow.EntryDtos.ShowEntryDto;

public class ShowConvertors {
    public static ShowEntity convertEntrytoEntity(ShowEntryDto showEntryDto){
        ShowEntity showEntity = ShowEntity.builder()
                .showDate(showEntryDto.getShowDate())
                .showTime(showEntryDto.getShowTime())
                .showType(showEntryDto.getShowType()).build();

        return showEntity;
    }

}
