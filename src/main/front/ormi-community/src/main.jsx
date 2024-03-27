import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Document from './routes/Document.jsx';
import Header from './components/Layout/Header';
import LogoHeader from './components/Layout/LogoHeader';
import Login from './routes/Login';

const router = createBrowserRouter([
  {
      path: "",
      element: (
        <Login></Login>
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
