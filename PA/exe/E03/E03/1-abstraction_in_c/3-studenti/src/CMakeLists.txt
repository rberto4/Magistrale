cmake_minimum_required(VERSION 3.30)
project(src C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(src
    adt_studentcorso2.c
    corso.c
    corso.h
    studente.c
    studente.h)
