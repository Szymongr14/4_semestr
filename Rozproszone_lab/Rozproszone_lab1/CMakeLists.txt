cmake_minimum_required(VERSION 3.27)
project(Rozproszone_lab1 C)

set(CMAKE_C_STANDARD 11)

add_executable(Rozproszone_lab1 main.c
        hash_table.h
        hash_table.c)
