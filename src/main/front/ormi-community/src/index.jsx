import { createContext, useEffect } from 'react';
import { useState } from 'react';

const GlobalContext = createContext({
  selectDocID: "",
  setSelectDocID: () =>{},
});


const ContextProvider = ({children}) =>{
    const [selectDocID, setSelectDocID] = useState("");

    return (
        <GlobalContext.Provider value={{
            selectDocID,
            setSelectDocID
        }}>
            {children}
        </GlobalContext.Provider>
    )
}


export {GlobalContext, ContextProvider}