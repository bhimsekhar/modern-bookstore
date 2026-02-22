export interface Employee {
  id: number;
  name: string;
  salary: number;
  department: string;
}

export interface EmployeeRequest {
  name: string;
  salary: number;
  department: string;
}
