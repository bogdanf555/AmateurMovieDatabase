import mysql.connector
from datetime import datetime
import sys


def displayTime():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(current_time)


insert_into_titles = """INSERT INTO titles (title_id, type, title_name, adult, `year`, duration)
                        VALUES (%s, %s, %s, %s, %s, %s)"""
insert_into_series = """INSERT INTO series (series_id, end_year)
                        VALUES (%s, %s)"""

imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()

movie_info = open(r"D:\workspace\IMDb data\titles.tsv", "r", encoding='utf-8')

if __name__ == '__main__':

    title_types = {}

    cursor_imdb_fake.execute("select * from title_types")

    for title_type in cursor_imdb_fake.fetchall():
        title_types[title_type[1]] = int(title_type[0])

    print(title_types)

    line = None
    movie_data = None

    for i in range(2):
        line = movie_info.readline()
        movie_data = line.strip('\n').split('\t')
        print(movie_data)

    displayTime()

    movie_record = []
    series_record = []

    while line:

        if len(movie_data[0]) != 10:
            line = movie_info.readline()
            movie_data = line.strip('\n').split('\t')
            continue

        if movie_data[1] == '\\N' or movie_data[2] == '\\N':
            line = movie_info.readline()
            movie_data = line.strip('\n').split('\t')
            continue

        series_end_year = movie_data[6] if movie_data[6] != '\\N' else None

        movie_data[1] = title_types[movie_data[1]]
        movie_data[2] = movie_data[2].replace("'", "''")

        if movie_data[4] == '\\N':
            movie_data[4] = None

        if movie_data[5] == '\\N':
            movie_data[5] = None

        if movie_data[7] == '\\N':
            movie_data[7] = None

        movie_data.pop(3)
        movie_data.pop(5)
        movie_data.pop(6)

        if movie_data[1] in (3, 13, 17, 18):
            series = [movie_data[0], series_end_year]
            series_record.append(series)

        movie_record.append(movie_data)

        line = movie_info.readline()
        movie_data = line.strip('\n').split('\t')

    displayTime()
    print(sys.getsizeof(movie_record) / 1024 / 1024 / 1024)
    print(len(movie_record))
    print(sys.getsizeof(series_record) / 1024 / 1024)
    print(len(series_record))

    try:

        title_inserts = len(movie_record)
        series_inserts = len(series_record)

        for i in range(title_inserts):

            try:
                cursor_imdb_fake.execute(insert_into_titles, movie_record[i])
            except mysql.connector.Error as err:
                print(err)

            if i % 100000 == 0:
                print(title_inserts - i)

        print("TITLES DONE")

        for i in range(series_inserts):

            try:
                cursor_imdb_fake.execute(insert_into_series, series_record[i])
            except mysql.connector.Error as err:
                print(err)

            if i % 50000 == 0:
                print(series_inserts - i)

        imdb_fake_db.commit()
    except mysql.connector.Error as err:
        print(err)

    cursor_imdb_fake.close()
    imdb_fake_db.close()

    displayTime()