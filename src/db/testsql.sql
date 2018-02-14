CREATE TABLE words (
id INT,
word VARCHAR(50) NULL,
PRIMARY KEY (id)
);

SELECT * from words where word like '%sheer%';


SELECT ID,WORD FROM PUBLIC.WORDS WHERE word = 'sheer';

SELECT COUNT(*) from PUBLIC.WORDS;
