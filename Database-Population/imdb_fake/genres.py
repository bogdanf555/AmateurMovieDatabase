import mysql.connector

movie_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='movie_db')

cursor_movie_db = movie_db.cursor()

imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()


if __name__ == '__main__':

    cursor_movie_db.execute("select genre_name from genres")

    genre_names = cursor_movie_db.fetchall()

    genre_names.remove(('N',))

    for current in genre_names:

        genre_name = current[0]
        cursor_imdb_fake.execute("insert into genres (genre_name) values ('{}')".format(genre_name))

    imdb_fake_db.commit()

    cursor_imdb_fake.close()
    cursor_movie_db.close()

    imdb_fake_db.close()
    movie_db.close()
    