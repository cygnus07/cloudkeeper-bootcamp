import { useState, useEffect } from 'react'

  const DigitalWatch = () => {
    const [time, setTime] = useState(formatTime(new Date()))

    useEffect(() => {
      const interval = setInterval(() => {
        setTime(formatTime(new Date()))
      }, 1000)

      return () => {
        clearInterval(interval)
      }
    }, [])

   function formatTime(date){
    return date.toLocaleTimeString()
   }

    return (
      <div className="digital-watch-container">
        <h1>Digital Watch</h1>
        <div className="time">
          {time}
        </div>
      </div>
    )
  }

  export default DigitalWatch
