cmake_minimum_required(VERSION 3.30)
project(test_c C)

set(CMAKE_C_STANDARD 11)

add_executable(test_c main.c CounterModulo.c)
