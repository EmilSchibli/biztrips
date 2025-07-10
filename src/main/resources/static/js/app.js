document.addEventListener('DOMContentLoaded', () => {
    // Use relative URL that works both locally and in Docker
    const API_URL = '/v1';

    const tripsList = document.getElementById('trips-list');
    const addTripForm = document.getElementById('add-trip-form');
    
    const meetingsSection = document.getElementById('meetings-section');
    const meetingsHeader = document.getElementById('meetings-header');
    const meetingsList = document.getElementById('meetings-list');
    const addMeetingForm = document.getElementById('add-meeting-form');

    let selectedTripId = null;

    // --- Business Trip Functions ---

    const fetchTrips = async () => {
        try {
            const response = await fetch(`${API_URL}/trips`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const trips = await response.json();
            renderTrips(trips);
        } catch (error) {
            console.error('Fehler beim Laden der Geschäftsreisen:', error);
            tripsList.innerHTML = '<li>Fehler beim Laden der Reisen. Läuft der Backend-Server auf Port 8082?</li>';
        }
    };

    const renderTrips = (trips) => {
        tripsList.innerHTML = '';
        if (trips.length === 0) {
            tripsList.innerHTML = '<li>Keine Geschäftsreisen gefunden.</li>';
            return;
        }
        trips.forEach(trip => {
            const li = document.createElement('li');
            li.innerHTML = `
                <span>
                    <strong>${trip.title}</strong> (${new Date(trip.startTrip).toLocaleDateString()})
                    <br><small>${trip.description}</small>
                </span>
                <div class="button-group">
                    <button class="details-btn" data-trip-id="${trip.id}" data-trip-title="${trip.title}">Meetings anzeigen</button>
                    <button class="delete-btn" data-trip-id="${trip.id}">Löschen</button>
                </div>
            `;
            tripsList.appendChild(li);
        });
    };

    addTripForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const destination = document.getElementById('trip-destination').value;
        const startDate = document.getElementById('trip-start-date').value;
        const endDate = document.getElementById('trip-end-date').value;

        try {
            const response = await fetch(`${API_URL}/trips`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ title: destination, description: "New Trip", startTrip: `${startDate}T09:00:00`, endTrip: `${endDate}T18:00:00`, meetings: [] }),
            });
            if (!response.ok) {
                 throw new Error(`HTTP error! status: ${response.status}`);
            }
            addTripForm.reset();
            fetchTrips();
        } catch (error) {
            console.error('Fehler beim Hinzufügen der Reise:', error);
        }
    });

    tripsList.addEventListener('click', async (e) => {
        const tripId = e.target.dataset.tripId;

        if (e.target.classList.contains('delete-btn')) {
            if (confirm('Soll diese Geschäftsreise wirklich gelöscht werden?')) {
                try {
                    const response = await fetch(`${API_URL}/trips/${tripId}`, { method: 'DELETE' });
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    fetchTrips();
                    if (selectedTripId === tripId) {
                        meetingsSection.style.display = 'none';
                        selectedTripId = null;
                    }
                } catch (error) {
                    console.error('Fehler beim Löschen der Reise:', error);
                }
            }
        }

        if (e.target.classList.contains('details-btn')) {
            const destination = e.target.dataset.tripTitle;
            selectedTripId = tripId;
            meetingsHeader.textContent = `Meetings für ${destination}`;
            meetingsSection.style.display = 'block';
            fetchMeetings(tripId);
        }
    });

    // --- Meeting Functions ---

    const fetchMeetings = async (tripId) => {
        try {
            const response = await fetch(`${API_URL}/trips/${tripId}/meetings`);
             if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const meetings = await response.json();
            renderMeetings(meetings);
        } catch (error) {
            console.error('Fehler beim Laden der Meetings:', error);
            meetingsList.innerHTML = '<li>Fehler beim Laden der Meetings.</li>';
        }
    };

    const renderMeetings = (meetings) => {
        meetingsList.innerHTML = '';
        if (meetings.length === 0) {
            meetingsList.innerHTML = '<li>Keine Meetings für diese Reise.</li>';
            return;
        }
        meetings.forEach(meeting => {
            const li = document.createElement('li');
            li.innerHTML = `
                <span><strong>${meeting.title}</strong><br><small>${meeting.description}</small></span>
                <button class="delete-btn" data-meeting-id="${meeting.id}">Löschen</button>
            `;
            meetingsList.appendChild(li);
        });
    };

    addMeetingForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        if (!selectedTripId) return;

        const title = document.getElementById('meeting-title').value;
        const description = document.getElementById('meeting-description').value;
        
        try {
            const response = await fetch(`${API_URL}/trips/${selectedTripId}/meetings`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ title, description }),
            });
            if (!response.ok) {
                 throw new Error(`HTTP error! status: ${response.status}`);
            }
            addMeetingForm.reset();
            fetchMeetings(selectedTripId);
        } catch(error) {
            console.error('Fehler beim Hinzufügen des Meetings:', error);
        }
    });

    meetingsList.addEventListener('click', async (e) => {
        if (e.target.classList.contains('delete-btn')) {
            const meetingId = e.target.dataset.meetingId;
            if (confirm('Soll dieses Meeting wirklich gelöscht werden?')) {
                try {
                    const response = await fetch(`${API_URL}/trips/${selectedTripId}/meetings/${meetingId}`, { method: 'DELETE' });
                     if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    fetchMeetings(selectedTripId);
                } catch (error) {
                    console.error('Fehler beim Löschen des Meetings:', error);
                    alert("Löschen des Meetings fehlgeschlagen.");
                }
            }
        }
    });

    // Initial load
    fetchTrips();
}); 