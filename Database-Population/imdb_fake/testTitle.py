import mysql.connector
from datetime import datetime
import sys


def displayTime():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(current_time)


imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()

movie_info = open(r"D:\workspace\IMDb data\titles.tsv", "r", encoding='utf-8')

if __name__ == '__main__':

    line = None
    movie_data = None

    for i in range(2):
        line = movie_info.readline()
        movie_data = line.strip('\n').split('\t')
        print(movie_data)

    displayTime()

    while line:

        if movie_data[0] == 'tt12957362':
            print("got it")
            exit(0)

        line = movie_info.readline()
        movie_data = line.strip('\n').split('\t')

    print("nope man")