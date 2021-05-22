import mysql.connector


imdb_fake_db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor_imdb_fake = imdb_fake_db.cursor()

if __name__ == '__main__':

    cursor_imdb_fake.execute("select title_id, title_name from titles "
                             "where title_name regexp '^.*\\'.*$'")

    titles = cursor_imdb_fake.fetchall()

    with open("problem.txt", "w") as f:
        for i in range(len(titles)):
            f.write(titles[i][0] + '\n')

    for i in range(len(titles)):

        some_title = titles[i][1].replace("'", "\\'")
        some_title = some_title.replace("\\'\\'", "\\'")

        #some_title = titles[i][1].replace('"', '\\"')
        #print(some_title)

        update_query = "update titles set title_name='{}' where title_id='{}'"\
            .format(some_title, titles[i][0])

        try:
            cursor_imdb_fake.execute(update_query)
        except:
            print(some_title)
            print(update_query)
            exit()

    imdb_fake_db.commit()
    cursor_imdb_fake.close()
    imdb_fake_db.close()