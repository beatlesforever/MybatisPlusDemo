DROP TABLE IF EXISTS contacts;
CREATE TABLE contacts (
      id INT PRIMARY KEY AUTO_INCREMENT,
      name VARCHAR(255) NOT NULL,
      phone VARCHAR(20) NOT NULL,
      email VARCHAR(255),
      image_content TEXT

);
