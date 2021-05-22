import mysql.connector
from datetime import datetime

imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()

movie_info = open(r"D:\workspace\IMDb data\titles.tsv", "r", encoding='utf-8')

line = None
movie_data = None

got_this_types = ['movie', 'short', 'tvepisode', 'tvseries', 'video']

for i in range(2):
    line = movie_info.readline()
    movie_data = line.strip('\n').split('\t')
    print(movie_data)

now = datetime.now()
current_time = now.strftime("%H:%M:%S")
print(current_time)

while line:

    title_type = movie_data[1]

    if title_type != '' and title_type != '\\N':

        if title_type not in got_this_types:
            got_this_types.append(title_type)

    line = movie_info.readline()
    movie_data = line.strip('\n').split('\t')


for title_type in got_this_types:

    try:
        cursor_imdb_fake.execute("select * from title_types where type_name='{}'".format(title_type))
    except mysql.connector.Error as err:
        print(err)

    if cursor_imdb_fake.fetchone() is None:
        try:
            cursor_imdb_fake.execute("insert into title_types(type_name) values ('{}')".format(title_type))
        except mysql.connector.Error as err:
            print(err)

imdb_fake_db.commit()
cursor_imdb_fake.close()
imdb_fake_db.close()

now = datetime.now()
current_time = now.strftime("%H:%M:%S")
print(current_time)