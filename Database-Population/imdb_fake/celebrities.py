import mysql.connector
from datetime import datetime


def displayTime():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(current_time)


insert_celeb = """insert into celebrities (celebrity_id, celebrity_name, birth_year, death_year)
                    values ('{}', '{}', {}, {})"""

celeb_info = open(r"D:\workspace\IMDb data\celebrities.tsv", "r", encoding='utf-8')

db = mysql.connector.connect(
    host='localhost', user='root', password='', database='imdb_fake')

cursor = db.cursor()

if __name__ == '__main__':

    cursor.execute("select * from professions")
    prof = cursor.fetchall()

    professions_dict = {}

    for i in range(len(prof)):
        professions_dict[prof[i][1]] = prof[i][0]

    line = None
    celeb_data = None

    for i in range(2):
        line = celeb_info.readline()
        celeb_data = line.strip('\n').split('\t')
        print(celeb_data)

    displayTime()

    print(professions_dict)

    count = 0

    while line:

        count += 1
        
        if count % 10000 == 0:
            displayTime()
            print(count)
        
        celeb_id = celeb_data[0]
        celeb_name = celeb_data[1].replace("'", "\\'")

        birth = celeb_data[2] if celeb_data[2] != '\\N' else None
        death = celeb_data[3] if celeb_data[3] != '\\N' else None

        try:
            if birth and death:
                cursor.execute(insert_celeb.format(celeb_id, celeb_name, birth, death))
            elif birth:
                cursor.execute(
                    "insert into celebrities (celebrity_id, celebrity_name, birth_year) values ('{}', '{}', {})"
                    .format(celeb_id, celeb_name, birth))
            elif death:
                cursor.execute(
                    "insert into celebrities (celebrity_id, celebrity_name, death_year) values ('{}', '{}', {})"
                    .format(celeb_id, celeb_name, death))
            else:
                cursor.execute(
                    "insert into celebrities (celebrity_id, celebrity_name) values ('{}', '{}')"
                    .format(celeb_id, celeb_name))
        except mysql.connector.Error as err:
            print(err)
            line = celeb_info.readline()
            celeb_data = line.strip('\n').split('\t')
            continue
        '''
        if celeb_data[4] != '\\N' and celeb_data[4] != '':
            try:
                professions = [professions_dict[x.replace("_", " ").title()] for x in celeb_data[4].split(',')]
            except:
                print(celeb_data[4])
                exit()
            insert_query = "insert into professions_of_celebrities (celebrity_id, profession_id) values "

            for i in range(len(professions) - 1):
                insert_query += "('" + celeb_id + "', " + str(professions[i]) + "), "

            if len(professions) > 0:
                insert_query += "('" + celeb_id + "', " + str(professions[len(professions) - 1]) + ");"
                
            try:
                cursor.execute(insert_query)
            except mysql.connector.Error as err:
                print(err)

        if celeb_data[5] != '\\N' and celeb_data[5] != '':
            knownFor = [x for x in celeb_data[5].split(',')]

            insert_query = "insert into known_for (celebrity_id, title_id) values "

            for i in range(len(knownFor) - 1):
                insert_query += "('" + celeb_id + "', '" + str(knownFor[i]) + "'), "

            if len(knownFor) > 0:
                insert_query += "('" + celeb_id + "', '" + str(knownFor[len(knownFor) - 1]) + "');"
                
            try:
                cursor.execute(insert_query)
            except mysql.connector.Error as err:
                print(err)
        '''
        line = celeb_info.readline()
        celeb_data = line.strip('\n').split('\t')

    db.commit()
    cursor.close()
    db.close()

    displayTime()
    print(count)