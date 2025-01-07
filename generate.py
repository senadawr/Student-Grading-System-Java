import sqlite3
import random
import names

def create_random_grade():
    return round(random.uniform(70.0, 99.0), 1)

def generate_students(num_students):
    conn = sqlite3.connect('students.db')
    cursor = conn.cursor()

    # create table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS students (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT UNIQUE NOT NULL,
            itec104 REAL NOT NULL,
            gec106 REAL NOT NULL,
            soslit REAL NOT NULL,
            cmsc202 REAL NOT NULL,
            cmsc203 REAL NOT NULL,
            itec105 REAL NOT NULL,
            math24 REAL NOT NULL,
            pathfit3 REAL NOT NULL
        )
    ''')

    # generate random students
    for _ in range(num_students):
        while True:
            try:
                student = (
                    names.get_full_name(),
                    create_random_grade(),
                    create_random_grade(),
                    create_random_grade(),
                    create_random_grade(),
                    create_random_grade(),
                    create_random_grade(),
                    create_random_grade(),
                    create_random_grade()
                )
                cursor.execute('''
                    INSERT INTO students (name, itec104, gec106, soslit, cmsc202, cmsc203, itec105, math24, pathfit3)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ''', student)
                break
            except sqlite3.IntegrityError:
                continue

    # commit and close connection
    conn.commit()
    conn.close()

if __name__ == '__main__':
    num_students = 100 
    generate_students(num_students)
    print(f"Generated {num_students} students in students.db")