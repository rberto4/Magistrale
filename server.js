const express = require('express');
const fs = require('fs').promises;
const path = require('path');
const app = express();
const port = 3000;

app.use(express.json());
app.use(express.static('.'));

// Endpoint per ottenere la lista dei corsi
app.get('/courses', async (req, res) => {
    try {
        const contentDir = path.join(__dirname, 'content');
        const courses = await fs.readdir(contentDir);
        const courseList = await Promise.all(courses.map(async (courseId) => {
            const courseName = courseId.split('-').map(word => 
                word.charAt(0).toUpperCase() + word.slice(1)
            ).join(' ');
            return { id: courseId, name: courseName };
        }));
        res.json(courseList);
    } catch (error) {
        console.error('Errore nel recupero dei corsi:', error);
        res.status(500).json({ error: 'Errore nel recupero dei corsi' });
    }
});

// Endpoint per creare un nuovo corso
app.post('/create-course', async (req, res) => {
    try {
        const { courseId, courseName } = req.body;
        
        // Crea le directory necessarie
        await fs.mkdir(path.join(__dirname, 'content', courseId), { recursive: true });
        await fs.mkdir(path.join(__dirname, 'pdfs', courseId), { recursive: true });
        
        // Crea un file index.html di base
        const indexHtml = `<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${courseName}</title>
</head>
<body>
    <h1>${courseName}</h1>
    <p>Inserisci qui i tuoi appunti.</p>
</body>
</html>`;
        
        await fs.writeFile(path.join(__dirname, 'content', courseId, 'index.html'), indexHtml);
        
        res.json({ success: true });
    } catch (error) {
        console.error('Errore nella creazione del corso:', error);
        res.status(500).json({ error: 'Errore nella creazione del corso' });
    }
});

// Endpoint per ottenere il contenuto di un corso
app.get('/content/:courseId', async (req, res) => {
    try {
        const { courseId } = req.params;
        const contentPath = path.join(__dirname, 'content', courseId, 'index.html');
        const content = await fs.readFile(contentPath, 'utf-8');
        res.send(content);
    } catch (error) {
        console.error('Errore nel recupero del contenuto:', error);
        res.status(500).json({ error: 'Errore nel recupero del contenuto' });
    }
});

// Endpoint per ottenere la lista dei PDF di un corso
app.get('/pdfs/:courseId', async (req, res) => {
    try {
        const { courseId } = req.params;
        const pdfsDir = path.join(__dirname, 'pdfs', courseId);
        const pdfs = await fs.readdir(pdfsDir);
        res.json(pdfs);
    } catch (error) {
        console.error('Errore nel recupero dei PDF:', error);
        res.status(500).json({ error: 'Errore nel recupero dei PDF' });
    }
});

app.listen(port, () => {
    console.log(`Server in esecuzione su http://localhost:${port}`);
}); 