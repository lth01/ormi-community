import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Document from './routes/Document.jsx';
import Header from './components/Layout/Header';
import LogoHeader from './components/Layout/LogoHeader';
import Login from './routes/Login';
import { Input } from './components/ui/input';
import LabelSection from './components/Layout/LabelSection';
import Signup from './routes/Signup/Signup';
import SignupComplete from './routes/Signup/SignupComplete';

const router = createBrowserRouter([
  {
      path: "",
      element: (
        <SignupComplete></SignupComplete>
      )
  },
  {
      path: "/doc",
      element: (
          <Document />
      )
  },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <RouterProvider router={router} />
)
