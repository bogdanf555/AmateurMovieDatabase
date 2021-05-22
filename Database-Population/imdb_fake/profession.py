import mysql.connector

movie_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='movie_db')

cursor_movie_db = movie_db.cursor()

imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()


if __name__ == '__main__':
    cursor_movie_db.execute("select profession_name from professions")

    professions_name = cursor_movie_db.fetchall()
    professions_name.remove(('', ))

    for prof in professions_name:

        name = prof[0]

        if name == '':
            continue

        name = name.replace('_', ' ').title()

        cursor_imdb_fake.execute("insert into professions(profession_name) values ('{}')".format(name))

    imdb_fake_db.commit()

    cursor_imdb_fake.close()
    cursor_movie_db.close()

    imdb_fake_db.close()
    movie_db.close()
