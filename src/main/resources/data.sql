DROP TABLE IF EXISTS modules;

CREATE TABLE modules (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  parent_id INT,
  module_level INT NOT NULL
);


INSERT INTO modules (id, name, parent_id, module_level) VALUES
  (100000, 'Module 1', NULL, 0);