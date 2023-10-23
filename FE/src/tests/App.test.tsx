/** @jest-environment jsdom */

import { render, screen } from "@testing-library/react";
import App from "../App";
import "@testing-library/jest-dom";
import { BrowserRouter } from "react-router-dom";

test("render App", () => {
  render(
    <BrowserRouter>
      <App />
    </BrowserRouter>,
  );
  const linkElement = screen.getByText(/INTRO/i);
  expect(linkElement).toBeInTheDocument();
});
