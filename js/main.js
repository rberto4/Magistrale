// Inizializzazione di PDF.js
pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js';

// Indice di ricerca
let searchIndex = null;
let searchData = [];
let courses = [];

// Inizializzazione
document.addEventListener('DOMContentLoaded', () => {
    initializeNavigation();
    initializeSearch();
    initializeAddCourse();
    loadCourses();
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

// Caricamento dei corsi esistenti
async function loadCourses() {
    try {
        const response = await fetch('/courses');
        if (response.ok) {
            courses = await response.json();
            updateCourseLinks();
            if (courses.length > 0) {
                loadCourseContent(courses[0].id);
            }
        }
    } catch (error) {
        console.error('Errore nel caricamento dei corsi:', error);
        courses = [];
    }
}

// Aggiornamento dei link dei corsi
function updateCourseLinks() {
    const courseLinks = document.getElementById('courseLinks');
    courseLinks.innerHTML = courses.map(course => `
        <a href="#" data-course="${course.id}">${course.name}</a>
    `).join('');
    
    // Aggiungi gli event listener ai nuovi link
    document.querySelectorAll('.nav-links a').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const course = e.target.dataset.course;
            loadCourseContent(course);
        });
    });
}

// Inizializzazione del modal di aggiunta corso
function initializeAddCourse() {
    const addCourseBtn = document.getElementById('addCourseBtn');
    const modal = document.getElementById('addCourseModal');
    const confirmBtn = document.getElementById('confirmAddCourse');
    const cancelBtn = document.getElementById('cancelAddCourse');
    const input = document.getElementById('newCourseName');

    addCourseBtn.addEventListener('click', () => {
        modal.classList.remove('hidden');
        input.focus();
    });

    cancelBtn.addEventListener('click', () => {
        modal.classList.add('hidden');
        input.value = '';
    });

    confirmBtn.addEventListener('click', async () => {
        const courseName = input.value.trim();
        if (courseName) {
            await addNewCourse(courseName);
            modal.classList.add('hidden');
            input.value = '';
        }
    });

    // Chiudi il modal con ESC
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && !modal.classList.contains('hidden')) {
            modal.classList.add('hidden');
            input.value = '';
        }
    });
}

// Aggiunta di un nuovo corso
async function addNewCourse(courseName) {
    const courseId = courseName.toLowerCase().replace(/\s+/g, '-');
    
    try {
        const response = await fetch('/create-course', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                courseId,
                courseName
            })
        });

        if (response.ok) {
            await loadCourses(); // Ricarica la lista dei corsi
        } else {
            throw new Error('Errore nella creazione del corso');
        }
    } catch (error) {
        console.error('Errore nell\'aggiunta del corso:', error);
        alert('Errore nell\'aggiunta del corso. Riprova più tardi.');
    }
}

// Caricamento del contenuto di un corso
async function loadCourseContent(courseId) {
    try {
        const response = await fetch(`/content/${courseId}`);
        if (!response.ok) throw new Error('Contenuto non trovato');
        
        const content = await response.text();
        document.getElementById('content').innerHTML = content;
        
        // Aggiorna l'indice di ricerca
        updateSearchIndex(courseId, content);
        
        // Evidenzia il codice
        document.querySelectorAll('pre code').forEach((block) => {
            hljs.highlightElement(block);
        });

        // Carica la lista dei PDF
        await loadPDFs(courseId);
    } catch (error) {
        console.error('Errore nel caricamento del contenuto:', error);
        document.getElementById('content').innerHTML = '<p>Errore nel caricamento del contenuto</p>';
    }
}

// Caricamento della lista dei PDF
async function loadPDFs(courseId) {
    try {
        const response = await fetch(`/pdfs/${courseId}`);
        if (!response.ok) throw new Error('PDF non trovati');
        
        const pdfs = await response.json();
        const pdfList = document.createElement('div');
        pdfList.className = 'pdf-list';
        
        if (pdfs.length > 0) {
            pdfList.innerHTML = `
                <h2>Slide disponibili</h2>
                <ul>
                    ${pdfs.map(pdf => `
                        <li>
                            <a href="#" onclick="loadPDF('/pdfs/${courseId}/${pdf}')">${pdf}</a>
                        </li>
                    `).join('')}
                </ul>
            `;
        }
        
        document.getElementById('content').appendChild(pdfList);
    } catch (error) {
        console.error('Errore nel caricamento dei PDF:', error);
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
function updateSearchIndex(courseId, content) {
    // Crea un documento per il parsing del contenuto HTML
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    
    // Estrai il testo e crea l'indice
    const text = doc.body.textContent;
    searchData.push({
        id: courseId,
        title: courseId,
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