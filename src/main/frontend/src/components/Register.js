import React, { useState } from 'react';
import {
    Container,
    Paper,
    TextField,
    Button,
    Typography,
    Box,
    Link
} from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import axios from 'axios';

function Register({ onRegister }) {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
        bio: ''
    });
    const [error, setError] = useState('');

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/register', formData);
            onRegister(response.data);
        } catch (err) {
            console.error('Registration error:', err.response?.data);
            setError(err.response?.data || 'Registration failed');
        }
    };

    return (
        <Container maxWidth="sm">
            <Paper elevation={3} sx={{ p: 4, mt: 8 }}>
                <Typography variant="h5" component="h1" gutterBottom>
                    Register
                </Typography>
                {error && (
                    <Typography color="error" gutterBottom>
                        {error}
                    </Typography>
                )}
                <Box component="form" onSubmit={handleSubmit}>
                    <TextField
                        fullWidth
                        label="Name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        margin="normal"
                        required
                    />
                    <TextField
                        fullWidth
                        label="Email"
                        name="email"
                        type="email"
                        value={formData.email}
                        onChange={handleChange}
                        margin="normal"
                        required
                    />
                    <TextField
                        fullWidth
                        label="Password"
                        name="password"
                        type="password"
                        value={formData.password}
                        onChange={handleChange}
                        margin="normal"
                        required
                    />
                    <TextField
                        fullWidth
                        label="Bio"
                        name="bio"
                        multiline
                        rows={3}
                        value={formData.bio}
                        onChange={handleChange}
                        margin="normal"
                    />
                    <Button
                        type="submit"
                        variant="contained"
                        fullWidth
                        sx={{ mt: 2 }}
                    >
                        Register
                    </Button>
                    <Box sx={{ mt: 2, textAlign: 'center' }}>
                        <Link component={RouterLink} to="/login">
                            Already have an account? Login
                        </Link>
                    </Box>
                </Box>
            </Paper>
        </Container>
    );
}

export default Register;