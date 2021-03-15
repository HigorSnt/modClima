package com.cyan.modclima.repositories;

import com.cyan.modclima.models.Farm;

import java.util.List;

public interface CustomRepository {

    List<Farm> list(String name, String code);

}
