import { Dollar } from "./components/dollar";

test("JEST 곱셈 테스트", () => {
  const dollar = new Dollar(5);

  dollar.times(2);

  expect(dollar.amount).toBe(10);
});
