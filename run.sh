#!/usr/bin/env bash
# run script for running the average_degree calculation
# I'll execute my programs, with the input directory tweet_input and output the files in the directory tweet_output
javac ./src/average_degree.java 
java ./src/average_degree ./tweet_input/tweets.txt ./tweet_output/output.txt
