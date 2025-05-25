// Inizializzazione di PDF.js
pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js';

// Indice di ricerca
let searchIndex = null;
let searchData = [];

// Inizializzazione
document.addEventListener('DOMContentLoaded', () => {
    initializeNavigation();
    initializeSearch();
    loadInitialContent();
});

// Gestione della navigazione
function initializeNavigation() {
    const navLinks = document.querySelectorAll('.nav-links a');
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const course = e.target.dataset.course;
            loadCourseContent(course);
        });
    });
}

// Inizializzazione della ricerca
function initializeSearch() {
    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', debounce(handleSearch, 300));
}

// Caricamento del contenuto iniziale
function loadInitialContent() {
    // Carica il primo corso per default
    loadCourseContent('ai');
}

// Caricamento del contenuto di un corso
async function loadCourseContent(course) {
    try {
        const response = await fetch(`content/${course}/index.html`);
        if (!response.ok) throw new Error('Contenuto non trovato');
        
        const content = await response.text();
        document.getElementById('content').innerHTML = content;
        
        // Aggiorna l'indice di ricerca
        updateSearchIndex(course, content);
        
        // Evidenzia il codice
        document.querySelectorAll('pre code').forEach((block) => {
            hljs.highlightElement(block);
        });
    } catch (error) {
        console.error('Errore nel caricamento del contenuto:', error);
        document.getElementById('content').innerHTML = '<p>Errore nel caricamento del contenuto</p>';
    }
}

// Visualizzazione PDF
async function loadPDF(pdfPath) {
    const pdfViewer = document.getElementById('pdfViewer');
    const content = document.getElementById('content');
    
    try {
        const loadingTask = pdfjsLib.getDocument(pdfPath);
        const pdf = await loadingTask.promise;
        
        // Nascondi il contenuto e mostra il visualizzatore PDF
        content.classList.add('hidden');
        pdfViewer.classList.remove('hidden');
        
        // Renderizza la prima pagina
        const page = await pdf.getPage(1);
        const scale = 1.5;
        const viewport = page.getViewport({ scale });
        
        const canvas = document.createElement('canvas');
        const context = canvas.getContext('2d');
        canvas.height = viewport.height;
        canvas.width = viewport.width;
        
        pdfViewer.innerHTML = '';
        pdfViewer.appendChild(canvas);
        
        await page.render({
            canvasContext: context,
            viewport: viewport
        }).promise;
    } catch (error) {
        console.error('Errore nel caricamento del PDF:', error);
        pdfViewer.innerHTML = '<p>Errore nel caricamento del PDF</p>';
    }
}

// Gestione della ricerca
function updateSearchIndex(course, content) {
    // Crea un documento per il parsing del contenuto HTML
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    
    // Estrai il testo e crea l'indice
    const text = doc.body.textContent;
    searchData.push({
        id: course,
        title: course,
        content: text
    });
    
    searchIndex = lunr(function() {
        this.field('title');
        this.field('content');
        this.ref('id');
        
        searchData.forEach(doc => {
            this.add(doc);
        });
    });
}

function handleSearch(e) {
    const query = e.target.value;
    if (!query || !searchIndex) return;
    
    const results = searchIndex.search(query);
    // Implementa qui la logica per mostrare i risultati della ricerca
    console.log('Risultati della ricerca:', results);
}

// Utility function per il debounce
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
} 