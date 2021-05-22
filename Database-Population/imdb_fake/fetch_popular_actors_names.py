import requests
import re
import mysql.connector
from bs4 import BeautifulSoup
from datetime import datetime


def displayTime():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(current_time)


movie_db = mysql.connector.connect(host='localhost', user='root', password='root', database='imdb_fake')

url = "https://www.imdb.com/list/ls008344500/?sort=list_order,asc&mode=detail&page="

update_celebrity = "update celebrities set description='{}', poster='{}' where celebrity_id='{}'"

if __name__ == '__main__':

    actors_array = []

    displayTime()

    for i in range(1, 4):

        page = requests.get(url + str(i))
        soup = BeautifulSoup(page.text, 'html.parser')

        actor_divs = soup.find_all(class_='lister-item mode-detail')

        for actor_div in actor_divs:



            pattern = '/name/(.+)/'
            result = re.match(pattern, actor_div.a['href'])

            actor_id = result.group(1)
            imageUrl = actor_div.img['src']

            pis = actor_div.find_all('p')
            try:
                description = pis[1].text.strip().replace("'", "\\'")
            except:
                continue
            actors_array.append((actor_id, imageUrl, description))

    print("fetched done")
    displayTime()

    cursor = movie_db.cursor()

    count = 0
    for actor in actors_array:
        count += 1

        if count % 100 == 0:
            print(count)

        try:
            cursor.execute(update_celebrity.format(actor[2], actor[1], actor[0]))
        except mysql.connector.Error as err:
            print(err)
            print(actor[0])

    print("database upload done")
    displayTime()

    movie_db.commit()
    cursor.close()
    movie_db.close()
