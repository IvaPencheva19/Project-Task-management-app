export interface TaskRequest {
    title: string;
    description: string;
    dueData: Date;
    assignedUserId: number;
    columnId: number;
}
