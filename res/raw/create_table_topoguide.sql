CREATE TABLE topoguide (
  _id INTEGER PRIMARY KEY AUTOINCREMENT,
  nom TEXT NOT NULL, 
  numero TEXT NOT NULL,
  remarques TEXT,
  sommet INT NOT NULL,
  depart INT NOT NULL
);

--  FOREIGN KEY(sommet) REFERENCES sommet(_id)