const express = require('express');
const fs = require('fs').promises;
const path = require('path');
const app = express();
const port = 3000;

app.use(express.json());
app.use(express.static('.'));

// Endpoint per creare un nuovo corso
app.post('/create-course', async (req, res) => {
    try {
        const { courseId, courseName } = req.body;
        
        // Crea le directory necessarie
        await fs.mkdir(path.join('content', courseId), { recursive: true });
        await fs.mkdir(path.join('pdfs', courseId), { recursive: true });
        
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
        
        await fs.writeFile(path.join('content', courseId, 'index.html'), indexHtml);
        
        res.json({ success: true });
    } catch (error) {
        console.error('Errore nella creazione del corso:', error);
        res.status(500).json({ error: 'Errore nella creazione del corso' });
    }
});

app.listen(port, () => {
    console.log(`Server in esecuzione su http://localhost:${port}`);
}); 