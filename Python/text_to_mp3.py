#!/usr/bin/python

import sys, getopt

import os
from gtts import gTTS


def convert_to_mp3(word, file, folder="new_words/"):
    if not os.path.exists(folder):
        os.makedirs(folder)

    tts = gTTS(text=word, lang='fr')
    tts.save(folder + file)


def main(argv):
    word = ''
    file = ''
    outputfolder = ''
    try:
        opts, args = getopt.getopt(argv, "hw:f:o:", ["word=", "ofolder="])
    except getopt.GetoptError:
        print('text_to_mp3.py -w <word> -f <file>  -o <outputFolder>')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print('text_to_mp3.py -w <word> -f <file> -o <outputFolder>')
            sys.exit()
        elif opt in ("-w", "--word"):
            word = arg
        elif opt in ("-f", "--file"):
            file = arg
        elif opt in ("-o", "--ofolder"):
            outputfolder = arg
    print('Word is "', word)
    print('File is "', file)
    print('Output folder is "', os.path.abspath(outputfolder))
    convert_to_mp3(word, file, outputfolder)
    print('Done')


# convert_words_file_to_mp3()

if __name__ == "__main__":
    main(sys.argv[1:])
