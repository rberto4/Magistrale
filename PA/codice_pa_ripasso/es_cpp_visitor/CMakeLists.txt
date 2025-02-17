cmake_minimum_required(VERSION 3.30)
project(es_cpp_visitor)

set(CMAKE_CXX_STANDARD 20)

add_executable(es_cpp_visitor main.cpp Visitor.cpp StampaSpettatoreVisitor.cpp spettatore.cpp)
