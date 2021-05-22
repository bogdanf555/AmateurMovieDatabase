import mysql.connector
from datetime import datetime
import pprint


def displayTime():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(current_time)


imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()

ratings_file = open(r"D:\workspace\IMDb data\titles.tsv", "r", encoding='utf-8')

if __name__ == '__main__':

    cursor_imdb_fake.execute("ALTER TABLE genres_of_titles AUTO_INCREMENT = 1")

    cursor_imdb_fake.execute("select * from genres")
    genres = cursor_imdb_fake.fetchall()

    genres_dict = {}
    for genre in genres:
        genres_dict[genre[1]] = genre[0]

    pprint.pprint(genres_dict)

    line = None
    title_data = None

    displayTime()

    for i in range(2):
        line = ratings_file.readline()
        title_data = line.strip('\n').split('\t')
        print(title_data)

    count = 0
    while line:

        count += 1

        if count % 1000000 == 0:
            print(count)

        if len(title_data[0]) != 10:
            line = ratings_file.readline()
            title_data = line.strip('\n').split('\t')
            continue

        title_id = title_data[0]
        genres = title_data[8].split(",") if title_data[8] != '\\N' else []

        if len(genres) < 1:
            line = ratings_file.readline()
            title_data = line.strip('\n').split('\t')
            continue

        genres = [genres_dict[x] for x in genres]

        insert_query = "insert into genres_of_titles (title_id, genre_id) values "

        for i in range(len(genres) - 1):
            insert_query += "('" + title_id + "', " + str(genres[i]) + "), "

        if len(genres) > 0:
            insert_query += "('" + title_id + "', " + str(genres[len(genres) - 1]) + ");"

        try:
            cursor_imdb_fake.execute(insert_query)
        except mysql.connector.Error as err:
            print(err)

        line = ratings_file.readline()
        title_data = line.strip('\n').split('\t')

    imdb_fake_db.commit()
    cursor_imdb_fake.close()
    imdb_fake_db.close()

    displayTime()