import mysql.connector
from datetime import datetime


def displayTime():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(current_time)


update_movie = """UPDATE titles SET rating={}, votes={}
                WHERE title_id='{}';
                """

imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()

ratings_file = open(r"D:\workspace\IMDb data\ratings.tsv", "r", encoding='utf-8')

if __name__ == '__main__':

    line = None
    movie_data = None

    displayTime()

    for i in range(2):
        line = ratings_file.readline()
        rating_data = line.strip('\n').split('\t')
        print(rating_data)

    count = 0
    while line:

        count += 1
        if count % 100000 == 0:
            print(count / 100000)

        try:
            rating_data[1] = float(rating_data[1])
        except:
            rating_data[1] = None

        try:
            rating_data[2] = int(rating_data[2])
        except:
            rating_data[2] = None

        cursor_imdb_fake.execute(update_movie.format(rating_data[1], rating_data[2], rating_data[0]))

        line = ratings_file.readline()
        rating_data = line.strip('\n').split('\t')

    imdb_fake_db.commit()
    cursor_imdb_fake.close()
    imdb_fake_db.close()

    displayTime()