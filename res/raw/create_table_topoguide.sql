CREATE TABLE topoguide (
  _id INTEGER PRIMARY KEY AUTOINCREMENT,
  nom TEXT NOT NULL, 
  access TEXT NOT NULL, 
  orientation TEXT NOT NULL, 
  numero TEXT NOT NULL,
  remarques TEXT,
  sommet INT
);

--  FOREIGN KEY(sommet) REFERENCES sommet(_id)