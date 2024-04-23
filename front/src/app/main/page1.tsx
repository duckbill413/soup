"use client"

import React, { useEffect, useRef, useState } from 'react';
import SecondMain from "@/components/mainpage/secondMain";
import FirstMain from "@/components/mainpage/firstMain";
import ThirdMain from "@/components/mainpage/thirdMain";
import FourthMain from "@/components/mainpage/fourthMain";
import "./App.css";
// import MainPageHeader from '@/components/mainpage/mainPageHeader'

export default function MainPage() {
  const outerDivRef = useRef<HTMLDivElement>(null);
  const [currentPage, setCurrentPage] = useState<number>(1);

  const scrollToPage = (page: number) => {
    const pageHeight = window.innerHeight*0.9;
    outerDivRef.current?.scrollTo({
      top: pageHeight * (page - 1),
      left: 0,
      behavior: "smooth",
    });
    setCurrentPage(page);
  };

  const handleClick = () => {
    if (currentPage < 4) {
      scrollToPage(currentPage + 1);
    }
  };

  useEffect(() => {
    const wheelHandler = (e: WheelEvent) => {
      e.preventDefault();
      if (!outerDivRef.current) return;

      const { deltaY } = e;
      // const { scrollTop } = outerDivRef.current;

      if (deltaY > 0 && currentPage < 4) {
        scrollToPage(currentPage + 1);
      } else if (deltaY < 0 && currentPage > 1) {
        scrollToPage(currentPage - 1);
      }
    };

    const outerDivRefCurrent = outerDivRef.current;
    if (outerDivRefCurrent) {
      outerDivRefCurrent.addEventListener('wheel', wheelHandler);
    }
    return () => {
      if (outerDivRefCurrent) {
        outerDivRefCurrent.removeEventListener('wheel', wheelHandler);
      }
    };
  }, [currentPage]);

  return (
    <div ref={outerDivRef} style={{height:'90vh', width:'100%', overflowY:'auto'}}>
      <FirstMain onButtonClick={handleClick}/>
      <SecondMain/>
      <ThirdMain/>
      <FourthMain/>
    </div>
  );
}

