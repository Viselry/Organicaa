import React from "react";

const PageNavigation = ({ currentPage, totalPages, onPageChange }) => {
  // Calculate page numbers to display
  const getPageNumbers = () => {
    const pageNumbers = [];
    
    // Always show 5 pages if possible
    let startPage = Math.max(0, currentPage - 2);
    let endPage = Math.min(totalPages - 1, currentPage + 2);
    
    // Ensure we always try to show 5 pages when possible
    if (endPage - startPage < 4 && totalPages > 5) {
      if (startPage === 0) {
        // We're at the beginning, extend the end
        endPage = Math.min(4, totalPages - 1);
      } else if (endPage === totalPages - 1) {
        // We're at the end, move the start earlier
        startPage = Math.max(0, totalPages - 5);
      }
    }
    
    for (let i = startPage; i <= endPage; i++) {
      pageNumbers.push(i);
    }
    
    return pageNumbers;
  };

  // If there are no pages or only one page, don't render navigation
  if (!totalPages || totalPages <= 1) {
    return null;
  }

  return (
    <div className="flex justify-center items-center mt-8 mb-4">
      <nav className="flex items-center">
        {/* First page button */}
        <button
          onClick={() => onPageChange(0)}
          disabled={currentPage === 0}
          className={`px-3 py-1 mx-1 rounded ${
            currentPage === 0
              ? "bg-gray-200 text-gray-500 cursor-not-allowed"
              : "bg-gray-200 hover:bg-gray-300 text-gray-700"
          }`}
        >
          &laquo;
        </button>
        
        {/* Previous page button */}
        <button
          onClick={() => onPageChange(currentPage - 1)}
          disabled={currentPage === 0}
          className={`px-3 py-1 mx-1 rounded ${
            currentPage === 0
              ? "bg-gray-200 text-gray-500 cursor-not-allowed"
              : "bg-gray-200 hover:bg-gray-300 text-gray-700"
          }`}
        >
          &lt;
        </button>
        
        {/* Page numbers */}
        {getPageNumbers().map((page) => (
          <button
            key={page}
            onClick={() => onPageChange(page)}
            className={`px-3 py-1 mx-1 rounded ${
              currentPage === page
                ? "bg-blue-600 text-white"
                : "bg-gray-200 hover:bg-gray-300 text-gray-700"
            }`}
          >
            {page + 1}
          </button>
        ))}
        
        {/* Next page button */}
        <button
          onClick={() => onPageChange(currentPage + 1)}
          disabled={currentPage === totalPages - 1}
          className={`px-3 py-1 mx-1 rounded ${
            currentPage === totalPages - 1
              ? "bg-gray-200 text-gray-500 cursor-not-allowed"
              : "bg-gray-200 hover:bg-gray-300 text-gray-700"
          }`}
        >
          &gt;
        </button>
        
        {/* Last page button */}
        <button
          onClick={() => onPageChange(totalPages - 1)}
          disabled={currentPage === totalPages - 1}
          className={`px-3 py-1 mx-1 rounded ${
            currentPage === totalPages - 1
              ? "bg-gray-200 text-gray-500 cursor-not-allowed"
              : "bg-gray-200 hover:bg-gray-300 text-gray-700"
          }`}
        >
          &raquo;
        </button>
      </nav>
    </div>
  );
};

export default PageNavigation;