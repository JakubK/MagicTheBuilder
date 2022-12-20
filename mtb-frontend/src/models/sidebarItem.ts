export interface SidebarItem {
  title: string;
  svg: any;
  routerLink?: string;
  isOuterLink?: true;
  items?: SidebarItem[]
}