CREATE TABLE itineraire (
  _id INTEGER PRIMARY KEY AUTOINCREMENT,
  voie TEXT NOT NULL, 
  orientation TEXT NOT NULL, 
  denivele INT NOT NULL, 
  difficulte_ski TEXT NOT NULL,
  difficulte_montee TEXT, 
  description TEXT NOT NULL,
  materiel TEXT,
  exposition INT,
  pente INT,
  duree_jour INTEGER,
  type TEXT,
  variante INT DEFAULT 0,
  topoguide INT
);

--FOREIGN KEY(topoguide) REFERENCES topoguide(_id)