import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import EditPassword from './routes/Password/EditPassword';
import DocumentWrite from './routes/Document/DocumentWrite';
import { Comment } from './components/Comment/Comment';
import { Document } from './components/Document/Document';
import Header from './components/Layout/Header';

const router = createBrowserRouter([
  {
      path: "",
      element: (
        <Document></Document>
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
