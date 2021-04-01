DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  content TEXT NOT NULL
);

INSERT INTO
  todo (content)
VALUES
  ('Brew coffee'),
  ('Wash the dog'),
  ('Make dinner');
