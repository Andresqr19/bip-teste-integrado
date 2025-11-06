export interface TransferRequest {
  fromId: number;
  toId: number;
  amount: number;
  lockingMode: 'PESSIMISTIC_WRITE'
}
