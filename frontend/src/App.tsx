import React, { useState } from 'react';
import axios from 'axios';

const App: React.FC = () => {
    const [message, setMessage] = useState('');
    const [retrievedMessage, setRetrievedMessage] = useState('');

    // Define the base URL for the backend API
    const backendUrl = 'http://sprout.connect/sproutbackend/api';

    // Function to save the message
    const handleSaveMessage = async () => {
        try {
            const response = await axios.post(`${backendUrl}`, { id: '1', content: message });
            console.log('Saved Message:', response.data);
        } catch (error) {
            console.error('Error saving message:', error);
        }
    };

    // Function to retrieve the message
    const handleGetMessage = async () => {
        try {
            const response = await axios.get(`${backendUrl}/1`);
            setRetrievedMessage(response.data);
        } catch (error) {
            console.error('Error retrieving message:', error);
        }
    };

    return (
        <div>
            <h1>Message App</h1>
            <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Enter your message"
            />
            <button onClick={handleSaveMessage}>Save Message</button>
            <button onClick={handleGetMessage}>Get Message</button>
            <p>Retrieved Message: {retrievedMessage}</p>
        </div>
    );
};

export default App;
