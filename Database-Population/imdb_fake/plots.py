import mysql.connector
import requests
from datetime import datetime
import json


def displayTime():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(current_time)


url = "https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/"

headers = {
    'x-rapidapi-key': "6fae38f5d6msh5243996cd2a9653p122166jsnc11c3ed7fb29",
    'x-rapidapi-host': "imdb-internet-movie-database-unofficial.p.rapidapi.com"
    }

imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()

if __name__ == '__main__':

    displayTime()

    cursor_imdb_fake.execute("""select title_id from titles 
                                where votes > 50000 and type=1 
                                order by rating desc
                                limit 10""")
    titles = cursor_imdb_fake.fetchall()
    displayTime()

    print(len(titles))
    print("loading starts...")

    file = open("problem.txt", "w")

    for i in range(len(titles)):

        try:
            response = requests.request("GET", url + titles[i][0], headers=headers)
            movie_dictionary_raw = json.loads(response.text)
            plot = movie_dictionary_raw['plot']
        except:
            try:
                file.write(titles[i][0])
            except:
                print(titles[i][0])
            continue

        if plot == '':
            print(titles[i][0])
            continue

        print(plot, titles[i][0])

        try:
            cursor_imdb_fake.execute("update titles set plot='{}' where title_id='{}'".format(plot, titles[i][0]))
        except mysql.connector.Error as err:
            print(err)

        if i % 1000 == 0:
            print(str(i) + ":")
            imdb_fake_db.commit()
            displayTime()

    imdb_fake_db.commit()
    cursor_imdb_fake.close()
    imdb_fake_db.close()

    displayTime()