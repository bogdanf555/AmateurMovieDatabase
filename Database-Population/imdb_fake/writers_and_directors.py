import mysql.connector
from datetime import datetime


def displayTime():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(current_time)


imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()

movie_file = open(r"D:\workspace\IMDb data\crew.tsv", "r", encoding='utf-8')

if __name__ == '__main__':

    line = None
    movie_data = None

    for i in range(2):
        line = movie_file.readline()
        movie_data = line.strip('\n').split('\t')
        print(movie_data)

    displayTime()

    while line:

        title_id = movie_data[0]

        if movie_data[1] != '\\N' and movie_data[1] != '':

            directors = movie_data[1].split(',')

            insert_query = "insert into directors_of_titles (title_id, director_id) values "

            for i in range(len(directors) - 1):
                insert_query += "('" + title_id + "', " + str(directors[i]) + "), "

            if len(directors) > 0:
                insert_query += "('" + title_id + "', " + str(directors[len(directors) - 1]) + ");"

            try:
                cursor_imdb_fake.execute(insert_query)
            except mysql.connector.Error as err:
                print(err)

        if movie_data[2] != '\\N' and movie_data[2] != '':

            writers = movie_data[1].split(',')

            insert_query = "insert into writers_of_titles (title_id, writer_id) values "

            for i in range(len(writers) - 1):
                insert_query += "('" + title_id + "', " + str(writers[i]) + "), "

            if len(writers) > 0:
                insert_query += "('" + title_id + "', " + str(writers[len(writers) - 1]) + ");"

            try:
                cursor_imdb_fake.execute(insert_query)
            except mysql.connector.Error as err:
                print(err)

        line = movie_file.readline()
        movie_data = line.strip('\n').split('\t')

    imdb_fake_db.commit()
    cursor_imdb_fake.close()
    imdb_fake_db.close()

    displayTime()