import React, { useState } from 'react';
import {
    Container,
    Paper,
    TextField,
    Button,
    Typography,
    Box
} from '@mui/material';
import axios from 'axios';

function Login({ onLogin }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                email,
                password
            });

            // Store user info in localStorage or session
            localStorage.setItem('user', JSON.stringify(response.data));

            // Call the onLogin callback
            onLogin(response.data);

        } catch (err) {
            setError('Invalid email or password');
        }
    };

    return (
        <Container maxWidth="sm">
            <Paper elevation={3} sx={{ p: 4, mt: 8 }}>
                <Typography variant="h5" component="h1" gutterBottom>
                    Login
                </Typography>
                {error && (
                    <Typography color="error" gutterBottom>
                        {error}
                    </Typography>
                )}
                <Box component="form" onSubmit={handleSubmit}>
                    <TextField
                        fullWidth
                        label="Email"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        margin="normal"
                        required
                    />
                    <TextField
                        fullWidth
                        label="Password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        margin="normal"
                        required
                    />
                    <Button
                        type="submit"
                        variant="contained"
                        fullWidth
                        sx={{ mt: 2 }}
                    >
                        Login
                    </Button>
                </Box>
            </Paper>
        </Container>
    );
}

export default Login;