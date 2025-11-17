import React, { useState } from 'react'
import '../App.css'

const Counter = () => {
  const [counter, setCounter] = useState(0)
  const increment = () => {
      setCounter(counter+1)
      // setCounter(prev => prev+1)
  }
  const decrement = () => setCounter(counter-1)
  const reset = () => {
    setCounter(0)
  }


  return (
    <div className='counter-container'>
        <h1 
          style={{
            color: counter > 0 ? 'green' : counter < 0 ? 'red' : 'gray'
          }}
        className='counter-title'>Counter: {counter} </h1>
        <div className="button-container">
          <button className= 'counter-btn'onClick={increment}> Increment </button>
        <button   className= 'counter-btn'onClick={decrement}> Decrement </button>
        <button   className= 'counter-btn'onClick={reset}> Reset </button>
        </div>
    </div>
  )
}

export default Counter